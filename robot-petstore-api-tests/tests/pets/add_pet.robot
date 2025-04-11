*** Settings ***
Resource          ../../resources/keywords/pet_keywords.robot
Resource          ../../resources/keywords/common_keywords.robot

*** Variables ***
${PET_NAME}      Cat1
${PET_STATUS}    available

*** Test Cases ***
Add New Pet
    [Documentation]           This test case adds a new pet and verifies its creation.
    [Tags]                    TC_ID_001
    Setup API Session
    Create Pet                ${PET_NAME}    ${PET_STATUS}
    Verify Pet Created        ${PET_NAME}    ${PET_STATUS}
