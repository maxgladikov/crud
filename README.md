Hello controller
Due to verify Jenkins CI please modify com.gladikov.crud.controller.HelloController.java
String hello = <some String>, commit and in one minute follow the link below
http://46.146.232.124:8080/crud/v1/api/university/hello/


Mentors
get all
curl "http://46.146.232.124:8080/crud/v1/api/university/mentors/"
get by contract number
curl "http://46.146.232.124:8080/crud/v1/api/university/mentors/?contract=c_001"
save one
curl -X POST "http://46.146.232.124:8080/crud/v1/api/university/mentors/" -H 'Content-Type: application/json' -d '{"firstName":"Ivan","lastName":"Lazarev","salary":100000.0,"contractNumber":"c_003"}'
update one
curl -X PUT "http://46.146.232.124:8080/crud/v1/api/university/mentors/" -H 'Content-Type: application/json' -d '{"firstName":"Ivan","lastName":"Lazarev","salary":500000.0,"contractNumber":"c_003"}'
verify updated
curl "http://46.146.232.124:8080/crud/v1/api/university/mentors/?contract=c_003"
delete one
curl -X DELETE "http://46.146.232.124:8080/crud/v1/api/university/mentors/?contract=c_003"
verify deleted
curl "http://46.146.232.124:8080/crud/v1/api/university/mentors/?contract=c_003"

Students
get all
curl "http://46.146.232.124:8080/crud/v1/api/university/students/"  | json_pp
get by contract number
curl "http://46.146.232.124:8080/crud/v1/api/university/students/?contract=pc_001" | json_pp
get by mentor id
curl "http://46.146.232.124:8080/crud/v1/api/university/students/mentor/?mentor_id=1" | json_pp
save one
curl -X POST "http://46.146.232.124:8080/crud/v1/api/university/students/" -H 'Content-Type: application/json' -d '{"firstName":"Nikolay","lastName":"Ushakov","age":37,"sex":"MALE","academicPerformance":3.2,"contractNumber":"pc_015","mentorId":1}' | json_pp
update one
curl -X PUT "http://46.146.232.124:8080/crud/v1/api/university/students/" -H 'Content-Type: application/json' -d '{"firstName":"Nikolay","lastName":"Ushakov","age":37,"sex":"MALE","academicPerformance":1.0,"contractNumber":"pc_015","mentorId":1}' | json_pp
verify updated
curl "http://46.146.232.124:8080/crud/v1/api/university/students/?contract=pc_015"
delete one
curl -X DELETE "http://46.146.232.124:8080/crud/v1/api/university/students/?contract=pc_015" | json_pp
verify deleted
curl "http://46.146.232.124:8080/crud/v1/api/university/students/?contract=pc_015" | json_pp


