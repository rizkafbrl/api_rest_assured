*** Settings ***
Library           RequestsLibrary
Library           Collections
Resource          ../variables/common_variables.robot

*** Keywords ***
Setup API Session
    [Documentation]    Sets up the API session for the Pet Store API.
    Create Session     petstore    ${BASE_URL}    verify=${FALSE}
    Disable Warnings

Disable Warnings
    [Documentation]    Disables insecure request warnings.
    Evaluate    __import__('urllib3').disable_warnings()    modules=urllib3

Get Json Value
    [Arguments]        ${json_data}    ${json_path}
    [Documentation]    Extracts values from JSON data using a JSONPath expression.
    ${matches}=        Evaluate    [match.value for match in __import__('jsonpath_ng').parse("${json_path}").find(${json_data})]
    Run Keyword If     len(${matches}) == 0    Fail    No matches found for JSONPath: ${json_path}
    RETURN             ${matches}