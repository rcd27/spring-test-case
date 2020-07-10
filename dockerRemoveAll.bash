# shellcheck disable=SC2046
docker rm -f $(docker ps -qa)
docker rmi springtestcase_api:latest springtestcase_approver:latest springtestcase_idgenerator:latest springtestcase_mailer:latest