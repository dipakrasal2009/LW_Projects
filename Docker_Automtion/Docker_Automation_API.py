from flask import Flask, jsonify
import subprocess

app = Flask(__name__)

@app.route("/pull/<imageName>")
def Docker_image_pull(imageName):
    cmd = f"docker pull {imageName}"
    output = subprocess.getstatusoutput(cmd)

    if output[0] == 0:
        return jsonify({
            "status": "success",
            "message": "Image downloaded successfully."
        })
    else:
        return jsonify({
            "status": "error",
            "message": "Image download failed.",
            "error": output[1]
        }), 500

@app.route("/run/<containerName>/<imageName>")
def Docker_image_run(containerName, imageName):
    cmd = f"docker run -dit --name {containerName} {imageName}"
    output = subprocess.getstatusoutput(cmd)

    if output[0] == 0:
        return jsonify({
            "status": "success",
            "message": f"Container '{containerName}' started successfully",
            "container_id": output[1]
        })
    else:
        return jsonify({
            "status": "error",
            "message": "Container start failed",
            "error": output[1]
        }), 500

@app.route("/stop/<containerID>")
def Docker_image_stop(containerID):
    cmd = f"docker stop {containerID}"
    output = subprocess.getstatusoutput(cmd)

    if output[0] == 0:
        return jsonify({
            "status": "success",
            "message": "Container stopped successfully",
            "container_id": output[1]
        })
    else:
        return jsonify({
            "status": "error",
            "message": "Container stop failed",
            "error": output[1]
        }), 500

@app.route("/list")
def Docker_container_list():
    cmd = "docker ps --format '{{json .}}' | jq -s"
    output = subprocess.getstatusoutput(cmd)

    if output[0] == 0:
        return jsonify({
            "status": "success",
            "containers":output[1]
        })
    else:
        return jsonify({
            "status": "error",
            "message": "Failed to list containers",
            "error": output[1]
        }), 500

@app.route("/start/<containerID>")
def Docker_container_start(containerID):
    cmd = f"docker start {containerID}"
    output = subprocess.getstatusoutput(cmd)

    if output[0] == 0:
        return jsonify({
            "status": "success",
            "message": f"Container '{containerID}' started successfully",
            "container_id": output[1]
        })
    else:
        return jsonify({
            "status": "error",
            "message": "Container start failed",
            "error": output[1]
        }), 500

@app.route("/remove/<containerID>")
def Docker_container_remove(containerID):
    # First stop the container if it's running
    stop_cmd = f"docker stop {containerID}"
    subprocess.getstatusoutput(stop_cmd)
    
    # Then remove the container
    cmd = f"docker rm {containerID}"
    output = subprocess.getstatusoutput(cmd)

    if output[0] == 0:
        return jsonify({
            "status": "success",
            "message": f"Container '{containerID}' removed successfully",
            "container_id": output[1]
        })
    else:
        return jsonify({
            "status": "error",
            "message": "Container removal failed",
            "error": output[1]
        }), 500

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=8080)
    
