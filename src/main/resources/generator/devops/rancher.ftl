backend:serviceName|envId|serviceId|lable|javaOpts|springBootFile:upgrade_develop.sh

${project.name}-api|1a5|1sTODO|${project.name}=true|-server -Xmx256m -Xms256m|--spring.profiles.active=test
${project.name}-springbootadmin|1a5|1sTODO|${project.name}=true|-server -Xmx256m -Xms256m|--spring.profiles.active=test
${project.name}-config|1a5|1sTODO|${project.name}=true|-server -Xmx32m -Xms32m|--spring.profiles.active=test,native
${project.name}-discovery|1a5|1sTODO|${project.name}=true|-server -Xmx128m -Xms128m|--spring.profiles.active=test
${project.name}-gateway|1a5|1sTODO|${project.name}=true|-server -Xmx256m -Xms256m|--spring.profiles.active=test
${project.name}-zipkin|1a5|1sTODO|${project.name}=true|-server -Xmx512m -Xms256m|--spring.profiles.active=test


${project.name}-task|1a5|1sTODO|${project.name}=true|-server -Xmx128m -Xms128m|--spring.profiles.active=test
${project.name}-admin|1a5|1sTODO|${project.name}=true|-server -Xmx256m -Xms256m|--spring.profiles.active=test
${project.name}-website|1a5|1sTODO|${project.name}=true|-server -Xmx256m -Xms256m|--spring.profiles.active=test

