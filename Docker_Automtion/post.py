
from flask import Flask,request,jsonify

app = Flask(__name__)

@app.route('/json',methods=['POST'])
def json():
    data = request.get_json()


    #data = {
       # 'name': 'diapk',
     #   'age': 21,
      #  'hobbies': ['searching', 'chatting', 'learning']
    #}


    return jsonify(data)

if __name__ == '__main__':
    app.run()
