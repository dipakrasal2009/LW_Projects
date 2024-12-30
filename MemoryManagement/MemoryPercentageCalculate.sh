#!/bin/bash

# Email configuration
EMAIL="dipakrasal2009@gmail.com"
SUBJECT="High Memory Usage Alert"

send_email() {
    local subject="$1"
    local body="$2"
    echo -e "$body" | mailx -s "$subject" "$EMAIL"
}

while :
do
    totalMemory=$(free -m | head -2 | tail -1 | awk '{print $2}')
    usedMemory=$(free -m | head -2 | tail -1 | awk '{print $3}')
    memPercentage=$(expr $usedMemory \* 100 / $totalMemory)

    # Log memory usage
    echo -e "$(date +"%r %D")   $memPercentage % " >> MemUses.txt

    if [[ $memPercentage -ge 90 ]]
    then 
        echo "security alarm for the memory" | espeak-ng
        echo "mem use is high"
        
        # Send critical alert
        EMAIL_BODY="CRITICAL ALERT: Memory usage is at ${memPercentage}%\nTimestamp: $(date)"
        send_email "$SUBJECT - CRITICAL (${memPercentage}%)" "$EMAIL_BODY"
        
    elif [[ $memPercentage -ge 50 ]]
    then
        echo "memory usage is getting high"
        
        # Send warning alert
        EMAIL_BODY="WARNING: Memory usage is at ${memPercentage}%\nTimestamp: $(date)"
        send_email "$SUBJECT - WARNING (${memPercentage}%)" "$EMAIL_BODY"
        
    else
        echo "memory is ok"
    fi
    
    sleep 10
done




