from flask import Flask, request, jsonify

from preprocess_data import run_model

app = Flask(__name__)

"""
    RULATI MAINU SI APELATI ENDPOINTU http://127.0.0.1:5000/python_bayes_classification SAU CARE II LOCALU VOSTRU CU POST
    LA PARAMETRU TREBE LISTA DE POEZII GEN:
    {
        "Songs": ["Song 1", "Song 2"]
    }
"""


@app.route('/python_bayes_classification', methods=['POST'])
def your_endpoint():
    try:
        # Assuming the input data is in JSON format
        input_data = request.get_json()
        model_response = run_model(input_data['Songs'])

        response_data = {'message': 'Success', 'result': model_response}

        return jsonify(response_data), 200

    except Exception as e:
        # Handle exceptions appropriately
        error_message = {'error': str(e)}
        return jsonify(error_message), 500


if __name__ == '__main__':
    app.run(debug=True)
