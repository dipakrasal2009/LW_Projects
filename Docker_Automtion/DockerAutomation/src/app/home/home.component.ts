import { Component, OnInit } from '@angular/core';
import { ConnectionService } from '../connection.service';
interface DockerContainer {
  id: string;
  name: string;
  image: string;
  status: string;
}
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  imageName: string = '';
  containerName: string = '';
  selectedImage: string = '';
  containers: DockerContainer[] = [];

  constructor(private connectionService: ConnectionService) {}

  ngOnInit() {
    this.loadContainers();
  }

  pullImage() {
    if (this.imageName) {
      this.connectionService.pullImage(this.imageName).subscribe({
        next: (response) => {
          console.log('Image pulled successfully');
          // Add success notification logic here
        },
        error: (error) => {
          console.error('Error pulling image:', error);
          // Add error notification logic here
        }
      });
    }
  }

  runContainer() {
    if (this.containerName && this.selectedImage) {
      this.connectionService.runContainer(this.containerName, this.selectedImage).subscribe({
        next: (response) => {
          console.log('Container started successfully');
          this.loadContainers();
        },
        error: (error) => {
          console.error('Error starting container:', error);
        }
      });
    }
  }

  startContainer(containerId: string) {
    this.connectionService.startContainer(containerId).subscribe({
      next: (response) => {
        console.log('Container started successfully');
        this.loadContainers();
      },
      error: (error) => {
        console.error('Error starting container:', error);
      }
    });
  }

  stopContainer(containerId: string) {
    this.connectionService.stopContainer(containerId).subscribe({
      next: (response) => {
        console.log('Container stopped successfully');
        this.loadContainers();
      },
      error: (error) => {
        console.error('Error stopping container:', error);
      }
    });
  }

  removeContainer(containerId: string) {
    this.connectionService.removeContainer(containerId).subscribe({
      next: (response) => {
        console.log('Container removed successfully');
        this.loadContainers();
      },
      error: (error) => {
        console.error('Error removing container:', error);
      }
    });
  }

  private loadContainers() {
    this.connectionService.getContainers().subscribe({
      next: (containers) => {
        this.containers = containers;
      },
      error: (error) => {
        console.error('Error loading containers:', error);
      }
    });
  }
}
