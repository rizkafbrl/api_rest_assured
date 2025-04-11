*** Settings ***
Resource          ../../resources/keywords/pet_keywords.robot

*** Variables ***
${PENDING}          pending
${AVALIABLE}        available
${PET_ID}           1

*** Test Cases ***
Find Pets By Status (Pending)
    [Documentation]       This test case retrieves pets with the status "pending" and verifies the response.
    ${response}=          Get Pets By Status    ${PENDING}
    Verify Pets Status    ${response}           ${PENDING}

Find Pets By Status (Available)
    [Documentation]       This test case retrieves pets with the status "available" and verifies the response.
    ${response}=          Get Pets By Status    ${AVALIABLE}
    Verify Pets Status    ${response}           ${AVALIABLE}

Retrieve Pet By ID
    [Documentation]       This test case retrieves a pet by its ID and verifies the response.
    ${response}=          Get Pet By ID         ${PET_ID}
    Verify Pet By ID      ${response}           ${PET_ID}