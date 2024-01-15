import re
import nltk
import pandas as pd
from nltk.stem.porter import PorterStemmer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB, MultinomialNB, BernoulliNB
from sklearn.preprocessing import LabelEncoder

# sentiment scores
SENTIMENT_ENUM = {-1: 'Very negative',
                  0: 'Negative',
                  1: 'Neutral',
                  2: 'Positive',
                  3: 'Very positive'}

# remove HTML tags, URLs, non-alphanumeric characters
def remove_tags(string):
    removelist = ""
    result = re.sub('', '', string)  # remove HTML tags
    result = re.sub('https://.*', '', result)  # remove URLs
    result = re.sub(r'[^w' + removelist + ']', ' ', result)  # remove non-alphanumeric characters
    result = result.lower()
    return result

# lemmatize (reduce words to their root form) the text
def lemmatize_text(text):
    w_tokenizer = nltk.tokenize.WhitespaceTokenizer()
    lemmatizer = nltk.stem.WordNetLemmatizer()
    st = ""
    for w in w_tokenizer.tokenize(text):
        st = st + lemmatizer.lemmatize(w) + " "
    return st

# stem (reduce words to their root form) the text
def stem_words(text):
    ps = PorterStemmer()
    stem_list = [ps.stem(word) for word in text.split()]
    text = ''.join(ps.stem(word) for word in text)

    return text

# process the data
def process_data(raw_data):
    nltk.download('stopwords')
    nltk.download('wordnet')
    from nltk.corpus import stopwords
    raw_data['Sentiment'].replace({'Very negative': -1, 'Negative': 0, 'Neutral': 1, 'Positive': 2, 'Very positive': 3},
                                  inplace=True)
    stop_words = set(stopwords.words('english'))
    raw_data['Song'] = raw_data['Song'].apply(
        lambda x: ' '.join([word for word in x.split() if word not in stop_words]))
    raw_data['Song'] = raw_data['Song'].apply(lemmatize_text)
    raw_data['Song'] = raw_data['Song'].apply(stem_words)
    return raw_data

# encode the labels
def encode_labels(raw_data):
    reviews = raw_data['Song'].values
    labels = raw_data['Sentiment'].values
    encoder = LabelEncoder()
    encoded_labels = encoder.fit_transform(labels)
    return reviews, encoded_labels

# train the model using Multinomial Naive Bayes
def train_model(raw_data, test_data: list):
    cv = CountVectorizer(max_features=800, stop_words='english')
    # vectorizing words and storing in variable X(predictor)
    X = cv.fit_transform(raw_data['Song']).toarray()
    y = raw_data.iloc[:, -1].values
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    mnb = MultinomialNB()
    mnb.fit(X_train, y_train)
    response = []
    with open("../Lyrics_SentimentAnalysis_DataMining/python_endpoint/output.txt", "w+") as file1:
        for song in test_data:
            file1.writelines(f'Song: {song},'
                        f'\n\tSentiment: {SENTIMENT_ENUM[mnb.predict(cv.transform([song]))[0]]}'
                        f'\n\tValue: {mnb.predict(cv.transform([song]))[0]}\n')
            response.append({"Song": song,
                             "Sentiment": SENTIMENT_ENUM[mnb.predict(cv.transform([song]))[0]],
                             "Value": str(mnb.predict(cv.transform([song]))[0])})

    return response

# run the model
def run_model(test_data):
    data = pd.read_csv('../Lyrics_SentimentAnalysis_DataMining/python_endpoint/training_set.csv')
    data = process_data(data)
    response = train_model(data, test_data)
    return response


if __name__ == '__main__':
    pass
