export class JsonOutput {
  data: any;
  message: string;

  constructor(data: any) {
    this.data = data;
    this.message = "Task is done successfully";
  }
}
