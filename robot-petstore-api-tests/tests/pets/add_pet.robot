*** Settings ***
Resource          ../../resources/keywords/pet_keywords.robot
Resource          ../../resources/keywords/common_keywords.robot

*** Variables ***
${PET_STATUS}     available

*** Test Cases ***
Add New Pet
    [Documentation]           This test case adds a new pet and verifies its creation.
    [Tags]                    TC_ID_001
    [Template]                Add And Verify Pet
    Cat1                      ${PET_STATUS}

Add New Pet 2
    [Documentation]           This test case adds a new pet and verifies its creation.
    [Tags]                    TC_ID_002
    [Template]                Add And Verify Pet
    Cat2                      ${PET_STATUS}

*** Keywords ***
Add And Verify Pet
    [Documentation]           This keywords will call the main keyword for add pet, this just the middle step that only used on this file
    [Arguments]               ${name}    ${status}
    Setup API Session
    Create Pet                ${name}    ${status}
    Verify Pet Created        ${name}    ${status}
