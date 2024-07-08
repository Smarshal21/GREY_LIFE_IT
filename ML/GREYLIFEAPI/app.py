from flask import Flask, request, jsonify
import numpy as np
import pickle
from flask_cors import CORS


app = Flask(__name__)
CORS(app)

# Load the trained model
with open('GREYLIFE/model.pkl', 'rb') as model_file:
    your_trained_model = pickle.load(model_file)
# Load the PCA object
with open('GREYLIFE/pca_object.pkl', 'rb') as pca_file:
    pca_object = pickle.load(pca_file)
# Load the StandardScaler
with open('GREYLIFE/scaler.pkl', 'rb') as scaler_file:
    sc = pickle.load(scaler_file)
# Function to preprocess input data
def preprocess_input(data):
    features = data['features']
    features_array = np.array(features).reshape(1, -1)
    features_std = sc.transform(features_array)
    features_pca = pca_object.transform(features_std)
    return features_pca


@app.route('/')
def index():
    return "Hello"

# Flask route to handle predictions
@app.route('/prediction', methods=['POST', 'GET'])
def predict():
    try:
        input_data = request.get_json()
        features_pca = preprocess_input(input_data)
        prediction = your_trained_model.predict(features_pca)
        return jsonify({'prediction': int(prediction[0])})
    except Exception as e:
        return jsonify({'error': str(e)})


if __name__ == '__main__':
    app.run()
