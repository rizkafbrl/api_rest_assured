*** Variables ***

${BASE_URL}                              https://petstore.swagger.io/v2
${PET_ENDPOINT}                          ${BASE_URL}/pet
${USER_ENDPOINT}                         ${BASE_URL}/user
${FIND_PETS_BY_STATUS_ENDPOINT}          ${PET_ENDPOINT}/findByStatus
${RETRIEVE_PET_BY_ID_ENDPOINT}           ${PET_ENDPOINT}

# Default user variables
${DEFAULT_USER_NAME}                     Cat2
${DEFAULT_USER_ID}                       12345
${DEFAULT_USER_EMAIL}                    cat2@example.com
${DEFAULT_USER_PASSWORD}                 Cat2Password

# Default pet variables
${DEFAULT_PET_NAME}                      Cat1
${DEFAULT_PET_STATUS}                    available