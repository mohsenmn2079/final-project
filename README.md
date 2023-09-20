# final-project
# Reporting and Submission API

This README provides documentation for the Reporting and Submission API, outlining the available endpoints and how to interact with them using CURL commands.

## Table of Contents

- [Introduction](#introduction)
- [Send Report and Submit](#send-report-and-submit)
- [Like Report](#like-report)
- [Dislike Report](#dislike-report)
- [Get Unapproved Report](#get-unapproved-report)
- [Set Unapproved Report Status Confirm](#set-unapproved-report-status-confirm)
- [Get Top Accident Hour](#get-top-accident-hour)

## Introduction
The Reporting and Submission API allows users to submit, like, and dislike reports, manage unapproved reports, and retrieve information about the top accident hour.

Before using the API, ensure you have the necessary authorization credentials and replace placeholders such as `'localhost:8080'` and `'bW9oc2VuOnBhc3N3b3Jk'` with the appropriate values for your specific environment.

## Send Report and Submit

Submit a report using the following CURL command:

```bash
curl --location 'localhost:8080/app/reports/submit' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic bW9oc2VuOnBhc3N3b3Jk' \
--data '{
    "title": "title 1",
    "description": "description 1",
    "point": {
        "coordinates": [
            36.307077,
            59.554082
        ],
        "type": "Point"
    },
    "type": "TYPE_ACCIDENT"
}'
```

## properties
- title: The title of the report.
- description: The description of the report.
- point: The geographical coordinates of the report.
- type: The type of report (TYPE_TRAFFIC, TYPE_ACCIDENT).

## Like Report

Use this endpoint to like a report.

```bash
curl --location --request POST 'localhost:8080/app/reports/like/1' \
--header 'Authorization: Basic bW9oc2VuOnBhc3N3b3Jk' \
--data ''
```
## DisLike Report

Use this endpoint to disLike a report.
```
curl --location --request POST 'localhost:8080/app/reports/dislike/1' \
--header 'Authorization: Basic bW9oc2VuOnBhc3N3b3Jk' \
--data ''
```
## Get Unapproved Report

Retrieve unapproved reports using the following CURL command:
```
curl --location 'localhost:8080/dashboard/reports/unapproved' \
--header 'Authorization: Basic bW9oc2VuOnBhc3N3b3Jk' \
--data ''
```

## Set Unapproved Report Status Confirm
Set the status of an unapproved report to "confirm" using the following CURL command:

```
curl --location --request POST 'localhost:8080/dashboard/reports/unapproved/1' \
--header 'Authorization: Basic bW9oc2VuOnBhc3N3b3Jk' \
--data ''
```
## Get Top Accident Hour
Retrieve information about the top accident hour using the following CURL command:

```
curl --location 'localhost:8080/app/reports/top-accident-hour' \
--header 'Authorization: Basic bW9oc2VuOnBhc3N3b3Jk' \
--data ''
```

This README provides a clear overview of the API endpoints and how to use them. You can customize it further based on your project's specific requirements and include any additional information that may be helpful to users.
