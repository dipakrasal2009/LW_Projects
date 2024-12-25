FROM redhat/ubi8

RUN yum install python3 -y

RUN pip3 install flask
RUN pip3 install flask-mysql

WORKDIR /myapp

COPY app.py  dipak.py

CMD ["python3","dipak.py"]

