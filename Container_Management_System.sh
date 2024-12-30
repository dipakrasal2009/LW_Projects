#/bin/bash

show_menu(){
    echo "Docker Management System"
    echo "1 start the container"
    echo "2 stop the container"
    echo "3 list of the container"
    echo "4 to run the container"
    echo "5 exit it"
}

start_container(){
    echo "enter the container name or id to start : "
    read container
    docker start "$container" && echo "Container $container started successfully!" || echo "failed to start the container $container"
}

stop_container(){
    echo "enter the container name or id to stop : "
    read container
    docker stop "$container" && echo "Container $container stopped successfully!" || echo "failed to stop the container $container"
}

list_container(){
    docker ps
}
run_container(){
	echo "enter the name or id to run the container"
	read container
	docker run -it $container
}

pull_container(){


while :; do
        show_menu
        read -p "choose an option : " choice
        case $choice in
            1)start_container;;
            2)stop_container;;
            3)list_container;;
	    4)run_container;;
            5)echo "exiting.....";exit 0;;
            *) echo "invalid Option. please try again...";;
        esac
done
