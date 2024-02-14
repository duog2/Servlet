Part 1:
- For part 1, after run mvn compile and mvn putty. There are two ways to test the program: using URL and cURL in powershell. Below is a few example for each method.
- http://localhost:8080/restappender/logs will return all current available logs
- Getlogs: http://localhost:8080/restappender/logs?limit=2&level=info or curl -X 'GET' \
                                                                       'http://localhost:8080/restappender/logs?limit=2&level=info' \
                                                                       -H 'accept: application/json'

To examine the postlog method, you can re-run http://localhost:8080/restappender/logs

-Delete: curl.exe -X 'DELETE' `
           'http://localhost:8080/restappender/'  `
           -H 'accept: */*'
To examine the Delete method, you can re-run http://localhost:8080/restappender/logs

Part3: For this part, you can use the below URL to see the stats, the cURL command will generate the file
- CSV: http://localhost:8080/restappender/stats/csv
       curl.exe -X 'GET' `
         'http://localhost:8080/restappender/stats/csv' `
         -H 'accept: */*'

- Excel: http://localhost:8080/restappender/stats/excel
         curl.exe -X 'GET' `
         'http://localhost:8080/restappender/stats/excel' `
         -H 'accept: */*'
- HTML: http://localhost:8080/restappender/stats/html
        curl.exe -X 'GET' `
         'http://localhost:8080/restappender/stats/html' `
         -H 'accept: */*'

- Note: if you modify the persistency, then you need to re-run the curl command then after that restart the putty and run URL to see changes.