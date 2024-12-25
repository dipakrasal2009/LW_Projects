FROM redhat/ubi8

RUN yum install python3 -y

#RUN yum install pip3 -y

RUN pip3 install flask

WORKDIR /myapp

COPY app.py  dipak.py

CMD ["python3","dipak.py"]

