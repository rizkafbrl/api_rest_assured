*** Settings ***
Library           RequestsLibrary
Library           Collections
Resource          ../variables/common_variables.robot
Resource          api_keywords.robot
Resource          common_keywords.robot

*** Keywords ***
Send POST Request
    [Arguments]     ${endpoint}    ${data}
    Setup API Session
    ${response}=    POST On Session    petstore    ${endpoint}    json=${data}
    RETURN    ${response}

Send GET Request
    [Arguments]     ${endpoint}    ${params}=${NONE}
    Setup API Session
    ${response}=    GET On Session    petstore    ${endpoint}    params=${params}
    RETURN          ${response}