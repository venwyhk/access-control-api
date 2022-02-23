# API gateway

server {
        listen 80;
        server_name api.${project.domain};
        return 301 https://$host$request_uri ;
}

server {
        listen 443 ssl;
        server_name api.${project.domain};
        ssl_certificate_key /etc/letsencrypt/live/api.${project.domain}/privkey.pem;
        ssl_certificate /etc/letsencrypt/live/api.${project.domain}/fullchain.pem;
        location / {
                proxy_pass http://127.0.0.1:9999;
                proxy_set_header Host $http_host;
                proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
        }
}


# SpringBoot Admin

server {
        listen 80;
        server_name springboot.${project.domain};
        return 301 https://$host$request_uri ;
}
server {
        listen 443 ssl;
        server_name springboot.${project.domain};
        ssl_certificate_key /etc/letsencrypt/live/springboot.${project.domain}/privkey.pem;
        ssl_certificate /etc/letsencrypt/live/springboot.${project.domain}/fullchain.pem;
        location / {
                proxy_pass http://localhost:7000;
                proxy_set_header Host $http_host;
                proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
        }
}

# Config Server

server {
        listen 80;
        server_name config.${project.domain};
        return 301 https://$host$request_uri ;
}

server {
        listen 443 ssl;
        server_name config.${project.domain};
        ssl_certificate_key /etc/letsencrypt/live/config.${project.domain}/privkey.pem;
        ssl_certificate /etc/letsencrypt/live/config.${project.domain}/fullchain.pem;
        location / {
                proxy_pass http://localhost:7777;
                proxy_set_header Host $http_host;
                proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
        }
}


# Discovery Server

server {
        listen 80;
        server_name discovery.${project.domain};
        return 301 https://$host$request_uri ;
}

server {
        listen 443 ssl;
        server_name discovery.${project.domain};
        ssl_certificate_key /etc/letsencrypt/live/discovery.${project.domain}/privkey.pem;
        ssl_certificate /etc/letsencrypt/live/discovery.${project.domain}/fullchain.pem;
        location / {
                proxy_pass http://localhost:8761;
                proxy_set_header Host $http_host;
                proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
        }
}


# ZipKin Server

server {
        listen 80;
        server_name zipkin.${project.domain};
        return 301 https://$host$request_uri ;
}

server {
        listen 443 ssl;
        server_name zipkin.${project.domain};
        ssl_certificate_key /etc/letsencrypt/live/zipkin.${project.domain}/privkey.pem;
        ssl_certificate /etc/letsencrypt/live/zipkin.${project.domain}/fullchain.pem;
        location / {
                proxy_pass http://localhost:8761;
                proxy_set_header Host $http_host;
                proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
        }
}


# website
server {
        listen 80;
        server_name www.${project.domain} ${project.domain};
        return 301 https://$host$request_uri ;
}

server {
        listen 443 ssl;
        server_name www.${project.domain} ${project.domain};
        ssl_certificate_key /etc/letsencrypt/live/www.${project.domain}/privkey.pem;
        ssl_certificate /etc/letsencrypt/live/www.${project.domain}/fullchain.pem;
        location / {
                proxy_pass http://localhost:8080;
                proxy_set_header Host $http_host;
                proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
        }
        location /api/ {                                                                                                                                                                                          rewrite ^/api/(.*) /$1 break;
           proxy_pass http://127.0.0.1:9999;
           proxy_http_version 1.1;
           proxy_set_header Upgrade $http_upgrade;
           proxy_set_header Connection 'upgrade';
           proxy_set_header Host $host;
           proxy_cache_bypass $http_upgrade;
        }
}



# admin ui
server {
        listen 80;
        server_name admin.${project.domain};
        return 301 https://$host$request_uri ;
}

server {
        listen 443 ssl;
        server_name admin.${project.domain};
        ssl_certificate_key /etc/letsencrypt/live/admin.${project.domain}/privkey.pem;
        ssl_certificate /etc/letsencrypt/live/admin.${project.domain}/fullchain.pem;
        root /home/ubuntu/deploy/${project.name}-admin;
        location / {
                proxy_pass http://localhost:19999;
                proxy_set_header Host $http_host;
                proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
        }
        location /api/ {                                                                                                                                                                                          rewrite ^/api/(.*) /$1 break;
           proxy_pass http://127.0.0.1:9999;
           proxy_http_version 1.1;
           proxy_set_header Upgrade $http_upgrade;
           proxy_set_header Connection 'upgrade';
           proxy_set_header Host $host;
           proxy_cache_bypass $http_upgrade;
        }
}
