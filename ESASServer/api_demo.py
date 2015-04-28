#!/usr/bin/env python3

import requests

# Register a provider: needs "firstname", "lastname", "email", "password"
json_request = {'firstname': 'Jane', 'lastname': 'Smith', 'email': 'jsmith@gmail.com', 'password': 'Password'}
response = requests.post('http://localhost:3888/register', json=json_request)
print(response.json())

# Register a patient: needs "firstname", "lastname", "email", "password", "providerid"
json_request = {'firstname': 'John', 'lastname': 'Doe', 'email': 'jdoe@gmail.com', 'password': 'password123', 'providerid': 1}
response = requests.post('http://localhost:3888/register', json=json_request)
print(response.json())

# Retrieve patient information from patient
response = requests.get('http://localhost:3888/v1/patients', auth=('jdoe@gmail.com', 'password123'))
print(response.json())

# Retrieve all patients information for the provider
response = requests.get('http://localhost:3888/v1/patients', auth=('jsmith@gmail.com', 'Password'))
print(response.json())

# Retrieve a specific patients information for the provider
response = requests.get('http://localhost:3888/v1/patients/1', auth=('jsmith@gmail.com', 'Password'))
print(response.json())

# Retrieve provider information from patient
response = requests.get('http://localhost:3888/v1/providers', auth=('jdoe@gmail.com', 'password123'))
print(response.json())

# Retrieve provider information from provder
response = requests.get('http://localhost:3888/v1/providers', auth=('jsmith@gmail.com', 'Password'))
print(response.json())

# Submit survey: needs "pain", "drowsiness", "nausea", "appetite", "shortnessofbreath", "depression", "anxiety", "wellbeing"
json_request = {'pain': 3, 'drowsiness': 4, 'nausea': 0, 'appetite': 7, 'shortnessofbreath': 2, 'depression': 1, 'anxiety': 1, 'wellbeing': 9}
response = requests.post('http://localhost:3888/v1/surveys', auth=('jdoe@gmail.com', 'password123'), json=json_request)
print(response.json())

# Submit survey with comments
json_request = {'pain': 3, 'drowsiness': 4, 'nausea': 0, 'appetite': 7, 'shortnessofbreath': 2, 'depression': 1, 'anxiety': 1, 'wellbeing': 9, 'comments': 'COMMENTS'}
response = requests.post('http://localhost:3888/v1/surveys', auth=('jdoe@gmail.com', 'password123'), json=json_request)
print(response.json())

# Retrieve surveys from patient
response = requests.get('http://localhost:3888/v1/surveys', auth=('jdoe@gmail.com', 'password123'))
print(response.json())

# Retrieve top surveys from provider
response = requests.get('http://localhost:3888/v1/surveys', auth=('jsmith@gmail.com', 'Password'))
print(response.json())

# Retrieve surveys for a patient from provider
response = requests.get('http://localhost:3888/v1/surveys/1', auth=('jsmith@gmail.com', 'Password'))
print(response.json())
