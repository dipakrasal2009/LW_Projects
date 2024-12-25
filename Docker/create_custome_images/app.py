from flask import Flask

app = Flask(__name__)

@app.route("/info")
def info():
    return "Hello From Dipak Rasal"

app.run()
~           
