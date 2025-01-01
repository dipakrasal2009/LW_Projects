from flask import Flask, jsonify, request 
import subprocess
app = Flask(__name__) 
  
@app.route('/pull',methods=['GET'])
def pullimage():
    imageName = request.args.get("imageName")
    
    print(imageName)

    output = subprocess.run(['docker', 'pull', imageName], stdout=subprocess.PIPE)
    
    
    if output.returncode == 0:
        return jsonify({
            "status": "success",
            "message": "Image downloaded successfully.",
            "output": str(output.stdout.decode('ascii'))
        })
    else:
        return jsonify({
            "status": "error",
            "message": "Image download failed.",
            "error": output.stderr
        }), 500
    
@app.route('/run',methods=['GET'])
def runImage():
    imageName = request.args.get("imageName")
    output = subprocess.run(['docker','run','-dit',imageName],stdout=subprocess.PIPE)

    if output.returncode == 0:
        return jsonify({
            "starus" : "success",
            "message":"image is now running.....",
            "output" :  str(output.stdout.decode('ascii'))
        })
    else:
        return jsonify({
            "status":"error",
            "message":"image running failed..",
            "error":output.stderr
        }),500
    




    
@app.route('/list',methods=['GET'])
def list():
    # #cmd = "docker ps --format '{{json .}}' | jq -s"
    # cmd = "docker ps "
    # # output = subprocess.run(['docker','ps'],stdout=subprocess.PIPE)
    # #cmd = docker image list --format "table {{.ID}}\t{{.Repository}}\t{{.Tag}}\t{{.Size}}"]
    # output = subprocess.getstatusoutput(cmd)

    # if output[0]== 0:
    #     return jsonify({
    #         "status":"success",
    #         "containers":output[1]
    #     })
    # else:
    #     return jsonify({
    #         "status":"error",
    #         "message":"Failed to list containers",
    #         "error":output[1]
    #     }), 500

    cmd = "docker ps --format '{{json .}}'"
    output = subprocess.getstatusoutput(cmd)

    if output[0] == 0:
        containers = output[1].splitlines()
        container_list = [eval(container) for container in containers]  # Convert each JSON string to a dictionary

        return jsonify({
            "status": "success",
            "containers": container_list
        })
    else:
        return jsonify({
            "status": "error",
            "message": "Failed to list containers",
            "error": output[1]
        }), 500

@app.route('/stop',methods=['GET'])
def stop():
    imageName = request.args.get("imageName")
     
    output = subprocess.run(['docker','stop',imageName],stdout=subprocess.PIPE)

    if output.returncode == 0:
        return jsonify({
            "starus" : "success",
            "message":"image is stopped....",
            "output" :  str(output.stdout.decode('ascii'))
        })
    else:
        return jsonify({
            "status":"error",
            "message":"image stoping failed..",
            "error":output.stderr
        }),500


@app.route('/start',methods=['GET'])
def start():
    imageName = request.args.get("imageName")
     
    output = subprocess.run(['docker','start',imageName],stdout=subprocess.PIPE)

    if output.returncode == 0:
        return jsonify({
            "starus" : "success",
            "message":"image is started....",
            "output" :  str(output.stdout.decode('ascii'))
        })
    else:
        return jsonify({
            "status":"error",
            "message":"image started failed..",
            "error":output.stderr
        }),500

@app.route('/search',methods=['GET'])
def search():
    # imageName = request.args.get("imageName")
     
    # output = subprocess.run(['docker','search',imageName],stdout=subprocess.PIPE)

    imageName = request.args.get("imageName")
    cmd = f"docker search {imageName} --format '{{json .}}'"
    output = subprocess.getstatusoutput(cmd)

    if output[0] == 0:
        containers = output[1].splitlines()
        container_list = [eval(container) for container in containers]  # Convert each JSON string to a dictionary

        return jsonify({
            "status": "success",
            "containers": container_list
        })
    else:
        return jsonify({
            "status":"error",
            "message":"can not get search result....",
            "error":output.stderr
        }),500

    

if __name__ == '__main__': 
    app.run(host='0.0.0.0',port=8080)