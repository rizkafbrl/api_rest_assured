*** Settings ***
Library                               RequestsLibrary
Library                               Collections
Library                               OperatingSystem
Resource                              api_keywords.robot
Resource                              ../variables/common_variables.robot

*** Keywords ***
Send POST Request
    [Arguments]       ${endpoint}        ${data}
    Create Session    petstore           ${BASE_URL}    verify=${FALSE}
    ${response}=      POST On Session    petstore       ${endpoint}         json=${data}
    RETURN            ${response}

Send GET Request
    [Arguments]       ${endpoint}        ${params}=${NONE}
    Create Session    petstore           ${BASE_URL}     verify=${FALSE}
    ${response}=      GET On Session     petstore        ${endpoint}        params=${params}
    RETURN            ${response}

Create Pet
    [Arguments]       ${name}                ${status}
    ${category}=      Create Dictionary      id=1        name=Cats
    ${payload}=       Create Dictionary    
    ...               name=${name}    
    ...               status=${status}
    ...               id=1234
    ...               category=${category}
    ${response}=      Send POST Request       ${PET_ENDPOINT}            ${payload}
    Should Be Equal As Numbers                ${response.status_code}    200

Verify Pet Created
    [Arguments]       ${name}    ${status}
    ${params}=        Create Dictionary       status=${status}
    ${response}=      Send GET Request        ${FIND_PETS_BY_STATUS_ENDPOINT}    ${params}
    ${json_response}=      Set Variable    ${response.json()}
    FOR    ${pet}    IN    @{json_response}
        ${pet_name}=    Set Variable    ${pet}[name]
        Run Keyword If    '${pet_name}' == '${name}'    Exit For Loop
    END
    Should Be Equal    ${pet_name}    ${name}    Pet was not found in the response

Get Pets By Status
    [Arguments]        ${status}
    ${response}=       GET On Session    petstore    ${FIND_PETS_BY_STATUS_ENDPOINT}    params=status=${status}
    RETURN             ${response}

Get Pet By ID
    [Arguments]        ${pet_id}
    ${response}=       GET On Session    petstore    ${RETRIEVE_PET_BY_ID_ENDPOINT}/${pet_id}
    RETURN             ${response}

Verify Pets Status
    [Arguments]        ${response}       ${expected_status}
    ${pets}=           Get Json Value    ${response.json()}    $[*]
    FOR    ${pet}    IN    @{pets}
        Should Be Equal As Strings    ${pet}[status]    ${expected_status}
    END

Verify Pet By ID
    [Arguments]        ${response}       ${expected_id}
    ${pets}=           Get Json Value    ${response.json()}    $[*]
    Run Keyword If    len(${pets}) == 0    Fail    No pets found in the response.
    ${pet}=    Set Variable    ${pets}[0]
    Log    Verifying pet: ${pet}    console=True
    Should Be Equal As Numbers    ${pet}[id]    ${expected_id}
    Should Not Be Empty    ${pet}[name]
    Should Not Be Empty    ${pet}[status]