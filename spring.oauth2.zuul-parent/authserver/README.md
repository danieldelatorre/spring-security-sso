#Using authorization code grant type:

#To ask for the Authorization_Code
#The user and password must be one of the users declared in the OAuth2SecurityConfiguration

http://localhost:9999/uaa/oauth/authorize?response_type=code&client_id=ui1&redirect_uri=http://client_host?key=value&scope=ui1.read

#To ask for the authorization token to request one resource

curl ui1:ui1-secret@localhost:9999/uaa/oauth/token -d grant_type=authorization_code -d client_id=ui1 -d redirect_uri=http://client_host?key=value -d code=nqEt5V

#Resource
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDcxNzA4MDYsInVzZXJfbmFtZSI6InN0ZXZlIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9FTkRfVVNFUiJdLCJqdGkiOiJmZjBmNWQwYi1kMDA4LTRhMmUtYTlmYy0wNThiM2JjYzZlY2EiLCJjbGllbnRfaWQiOiJ1aTEiLCJzY29wZSI6WyJ1aTEucmVhZCJdfQ.A6aGuqlQ3CRhdJorKD7jYtOkkudavsn5Rkfj2R9_7Ukd982KdL5LC-amBXyAxcQMtNnGiD4_8rhK8r6jVpW3vfqPhA9Hc1Gs70BtU10bS382trvEcGv-YnU8-noYZIb-qA_Ime9Hpr9QY4fyCOLeptzb0izUORGUO7NZRrhNc1UwDKTCWpkxj5IYi_hDn8lCDvA8KEhpA6aGfOpJX_11EWZTfLB_mgLWlqverDvIHEWOsbTDVTqtL6TNk0pu6M26rktPWMkCvUh-8JSWXjwoFj1W3BZtvkzkC4dDdTzIF-x3-nvZzpFONbZky-mzn42zvNa7aGPAb2h4lCWAFkJM6w" -X GET http://localhost:9001/resource/test
#-------------------------------------------------------------------------------------------------------------------
#Using Client Credentials grant type

#login and get the access token
curl ui1:ui1-secret@localhost:9999/uaa/oauth/token -d grant_type=client_credentials

#use the TOKEN when calling the ResourceServer
curl -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTUwNzIzMjA3MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9DTElFTlQiXSwianRpIjoiMTQzOThjNzAtNWFjZS00MTgzLWIzNWQtZTkzOWZiNDUyZDRjIiwiY2xpZW50X2lkIjoiY3VzdG9tZXItaW50ZWdyYXRpb24tc3lzdGVtIn0.F1VBArU5kyT0KIdNggtqObLM2K8jGJ6OaoENgswVUIP85vboYp_vfgTH8OQD2yaJtAjQaLdm_5T5dgF471xZQswaRnD5FIQivYAciz-vnWUdtvZnYMRDF80hRXCu5IH516AP-xofgxAaXPPGf-QGLgjufwx6LnA3Vk0C6YUF_BymMdbwoLrPg3SGjD_HA0nJmzhsoEVwauNuTnWplbXxQfTGplIQZAdwUKk6ibYwfUajk1vk7JrlLGvQV6gXEaLTJCUP2jbvJeQpl5pDtMEcSKXAdO_tC1CaVxw2eRpjkyW3gLK9bDnzbfaWUSJHxT-rdbQDO39kE4JofklamjyEug" -v localhost:9001/resource/test

#Password credentials
curl -X POST -vu ui1:ui1-secret http://localhost:9999/uaa/oauth/token -H "Accept: application/json" -d "client_id=ui1&grant_type=password&username=steve&password=password"

