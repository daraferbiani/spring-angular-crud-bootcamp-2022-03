import { Component, OnInit } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {MasterService} from "../services/master.service";

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})
export class DeleteComponent implements OnInit {

  constructor(private formBuild: FormBuilder, private mast:MasterService) { }

  ngOnInit(): void {

  }

}