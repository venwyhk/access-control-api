version: '2'
services:
  ${project.name}-springbootadmin:
    privileged: true
    image: dockerhub.cfg-global.com/${project.name}-springbootadmin:jenkins-1
    environment:
      JAVA_OPTS: -server -Xmx256m -Xms128m
      SPRING_BOOT_PROFILE: --spring.profiles.active=test
    stdin_open: true
    network_mode: host
    volumes:
    - /mnt/data/logs:/mnt/data/logs
    - /home/ubuntu/logs:/home/ubuntu/logs
    - /home/ubuntu/data:/home/ubuntu/data
    tty: true
    labels:
      io.rancher.scheduler.affinity:host_label: ${project.name}=true
      io.rancher.container.hostname_override: container_name
      io.rancher.container.pull_image: always
      ecommerce: 'true'
  ${project.name}-api:
    privileged: true
    image: dockerhub.cfg-global.com/${project.name}-api:jenkins-1
    environment:
      JAVA_OPTS: -server -Xmx256m -Xms128m
      SPRING_BOOT_PROFILE: --spring.profiles.active=test
    stdin_open: true
    network_mode: host
    volumes:
    - /mnt/data/logs:/mnt/data/logs
    - /home/ubuntu/logs:/home/ubuntu/logs
    - /home/ubuntu/data:/home/ubuntu/data
    tty: true
    labels:
      io.rancher.scheduler.affinity:host_label: ${project.name}=true
      io.rancher.container.hostname_override: container_name
      io.rancher.container.pull_image: always
  ${project.name}-config:
    privileged: true
    image: dockerhub.cfg-global.com/${project.name}-config:jenkins-1
    environment:
      JAVA_OPTS: -server -Xmx256m -Xms128m
      SPRING_BOOT_PROFILE: --spring.profiles.active=native,test
    stdin_open: true
    network_mode: host
    volumes:
    - /mnt/data/logs:/mnt/data/logs
    - /home/ubuntu/logs:/home/ubuntu/logs
    - /home/ubuntu/data:/home/ubuntu/data
    tty: true
    labels:
      io.rancher.scheduler.affinity:host_label: ${project.name}=true
      io.rancher.container.hostname_override: container_name
      io.rancher.container.pull_image: always
      ecommerce: 'true'
  ${project.name}-discovery:
    privileged: true
    image: dockerhub.cfg-global.com/${project.name}-discovery:jenkins-1
    environment:
      JAVA_OPTS: -server -Xmx256m -Xms128m
      SPRING_BOOT_PROFILE: --spring.profiles.active=test
    stdin_open: true
    network_mode: host
    volumes:
    - /mnt/data/logs:/mnt/data/logs
    - /home/ubuntu/logs:/home/ubuntu/logs
    - /home/ubuntu/data:/home/ubuntu/data
    tty: true
    labels:
      io.rancher.scheduler.affinity:host_label: ${project.name}=true
      io.rancher.container.hostname_override: container_name
      io.rancher.container.pull_image: always
  ${project.name}-gateway:
    privileged: true
    image: dockerhub.cfg-global.com/${project.name}-gateway:jenkins-1
    environment:
      JAVA_OPTS: -server -Xmx256m -Xms128m
      SPRING_BOOT_PROFILE: --spring.profiles.active=test
    stdin_open: true
    network_mode: host
    volumes:
    - /mnt/data/logs:/mnt/data/logs
    - /home/ubuntu/logs:/home/ubuntu/logs
    - /home/ubuntu/data:/home/ubuntu/data
    tty: true
    labels:
      io.rancher.scheduler.affinity:host_label: ${project.name}=true
      io.rancher.container.hostname_override: container_name
      io.rancher.container.pull_image: always
  ${project.name}-zipkin:
    privileged: true
    image: dockerhub.cfg-global.com/${project.name}-zipkin:jenkins-1
    environment:
      JAVA_OPTS: -server -Xmx512m -Xms256m
      SPRING_BOOT_PROFILE: --spring.profiles.active=test
    stdin_open: true
    network_mode: host
    volumes:
    - /mnt/data/logs:/mnt/data/logs
    - /home/ubuntu/logs:/home/ubuntu/logs
    - /home/ubuntu/data:/home/ubuntu/data
    tty: true
    labels:
      io.rancher.scheduler.affinity:host_label: ${project.name}=true
      io.rancher.container.hostname_override: container_name
      io.rancher.container.pull_image: always
  ${project.name}-task:
    privileged: true
    image: dockerhub.cfg-global.com/${project.name}-task:jenkins-1
    environment:
      JAVA_OPTS: -server -Xmx256m -Xms128m
      SPRING_BOOT_PROFILE: --spring.profiles.active=test
    stdin_open: true
    network_mode: host
    volumes:
    - /mnt/data/logs:/mnt/data/logs
    - /home/ubuntu/logs:/home/ubuntu/logs
    - /home/ubuntu/data:/home/ubuntu/data
    tty: true
    labels:
      io.rancher.scheduler.affinity:host_label: ${project.name}=true
      io.rancher.container.hostname_override: container_name
      io.rancher.container.pull_image: always
  ${project.name}-website:
    privileged: true
    image: dockerhub.cfg-global.com/${project.name}-website:jenkins-1
    environment:
      JAVA_OPTS: -server -Xmx256m -Xms128m
      SPRING_BOOT_PROFILE: --spring.profiles.active=test
    stdin_open: true
    network_mode: host
    volumes:
    - /mnt/data/logs:/mnt/data/logs
    - /home/ubuntu/logs:/home/ubuntu/logs
    - /home/ubuntu/data:/home/ubuntu/data
    tty: true
    labels:
      io.rancher.scheduler.affinity:host_label: ${project.name}=true
      io.rancher.container.hostname_override: container_name
      io.rancher.container.pull_image: always
  ${project.name}-admin:
    privileged: true
    image: dockerhub.cfg-global.com/${project.name}-admin:jenkins-1
    environment:
      JAVA_OPTS: -server -Xmx256m -Xms128m
      SPRING_BOOT_PROFILE: --spring.profiles.active=test
    stdin_open: true
    network_mode: host
    volumes:
    - /mnt/data/logs:/mnt/data/logs
    - /home/ubuntu/logs:/home/ubuntu/logs
    - /home/ubuntu/data:/home/ubuntu/data
    tty: true
    labels:
      io.rancher.scheduler.affinity:host_label: ${project.name}=true
      io.rancher.container.hostname_override: container_name
      io.rancher.container.pull_image: always