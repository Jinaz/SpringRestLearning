curl -v http://localhost:8080/users
curl -v -X DELETE localhost:8080/users/1
curl -v -X PUT localhost:8080/users/3 -H 'Content-Type:application/json' -d '{"prename": "Cali", "surname": "3", "message": "hello 3!"}'

curl -v http://localhost:8080/insertions
curl -v -X DELETE http://localhost:8080/insertions/4/cancel
curl -v -X PUT localhost:8080/insertions/4/complete