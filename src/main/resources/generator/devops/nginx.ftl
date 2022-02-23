# API gateway

server {
        listen 80;
        server_name api.${project.domain};
        location / {
                proxy_pass http://127.0.0.1:9999;
                proxy_set_header Host $http_host;
                proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
        }
}


# SpringBoot Admin

server {
        listen 80;
        server_name springbootadmin.${project.domain};
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
        location / {
                proxy_pass http://localhost:9411;
                proxy_set_header Host $http_host;
                proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
        }
}


# website
server {
        listen 80;
        server_name www.${project.domain} ${project.domain};
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
