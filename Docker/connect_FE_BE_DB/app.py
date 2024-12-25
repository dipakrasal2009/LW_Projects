from flask import Flask
from flaskext.mysql import MySQL


app = Flask(__name__)


ip="172.17.0.3"
user="dipak"
password="dipak"
dbname="lwdb"

app.config["MYSQL_DATABASE_USER"] = user
app.config["MYSQL_DATABASE_HOST"] = ip
app.config["MYSQL_DATABASE_PASSWORD"] = password
app.config["MYSQL_DATABASE_DB"] = dbname

mysql = MySQL()
mysql.init_app(app)


@app.route("/data")
def info():
    conn = mysql.connect()
    cursor = conn.cursor()
    cursor.execute("select * from student")
    data = cursor.fetchall()
    return str(data)


app.run(host='0.0.0.0')

