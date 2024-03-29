import json

from flask import Flask, request, jsonify

from preprocess_data import run_model

app = Flask(__name__)

"""
    In order to run this endpoint, you need to install Flask:
        pip install flask
    Then, run this file:
        python main.py
    You should see a message like:
        Running on http://127.0.0.1:5000/python_bayes_classification (Press CTRL+C to quit)
    Which means that the endpoint is running.
    To test it, you can use Postman or any other tool.
    You create a new GET request to the endpoint http://127.0.0.1:5000/python_bayes_classification
    The response will be a JSON object with the following structure:
        {
            "message": "Success",
            "result": "The result of the model"
        }
"""

@app.route('/python_bayes_classification', methods=['GET'])
def your_endpoint():
    try:
        json_file_path = 'python_endpoint\\test.json'
        with open(json_file_path, 'r') as json_file:
            input_data = json.load(json_file)
            
        model_response = run_model(input_data['Songs'])

        response_data = {'message': 'Success', 'result': model_response}

        return jsonify(response_data), 200

    except Exception as e:
        # Handle exceptions appropriately
        error_message = {'error': str(e)}
        return jsonify(error_message), 500


if __name__ == '__main__':
    app.run(debug=True)
