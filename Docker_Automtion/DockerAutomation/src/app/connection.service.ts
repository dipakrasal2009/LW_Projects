import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConnectionService {
  private apiUrl = 'http://192.168.1.104:83'; // Assuming you have a backend server running on port 3000

  constructor(private http:HttpClient) {}
  // Get list of all containers
  getContainers(): Observable<any> {
    return this.http.get(`${this.apiUrl}/containers`);
  }

  // Pull a Docker image
  pullImage(imageName: string): Observable<any> {
    console.log(imageName)
    return this.http.post(`${this.apiUrl}/pull/`, { imageName });
  }

  // Run a new container
  runContainer(containerName: string, imageName: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/containers/run`, {
      containerName,
      imageName
    });
  }

  // Start a container
  startContainer(containerId: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/containers/${containerId}/start`, {});
  }

  // Stop a container
  stopContainer(containerId: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/containers/${containerId}/stop`, {});
  }

  // Remove a container
  removeContainer(containerId: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/containers/${containerId}`);
  }

  // Get list of available images
  getImages(): Observable<any> {
    return this.http.get(`${this.apiUrl}/images`);
  }

  // Additional helper methods

  // Get container logs
  getContainerLogs(containerId: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/containers/${containerId}/logs`);
  }

  // Get container stats
  getContainerStats(containerId: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/containers/${containerId}/stats`);
  }

  // Restart a container
  restartContainer(containerId: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/containers/${containerId}/restart`, {});
  }

  // Pause a container
  pauseContainer(containerId: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/containers/${containerId}/pause`, {});
  }

  // Unpause a container
  unpauseContainer(containerId: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/containers/${containerId}/unpause`, {});
  }
}
