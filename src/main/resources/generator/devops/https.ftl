#!/usr/bin/expect -f
spawn sudo certbot-auto certonly --standalone
expect "Please enter in your domain name(s) (comma and/or space separated)  (Enter 'c' to cancel):"
send "trader.intelliware.com\r"

interact
