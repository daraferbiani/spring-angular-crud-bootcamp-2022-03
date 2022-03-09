import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Category} from "../model/category.model";
import {MasterService} from "../services/master.service";
import {error} from "@angular/compiler/src/util";
import {literal} from "@angular/compiler/src/output/output_ast";

@Component({
  selector: 'app-beranda',
  templateUrl: './beranda.component.html',
  styleUrls: ['./beranda.component.css']
})
export class BerandaComponent implements OnInit {
  list!: products[]


  constructor(private mast: MasterService) { }

  ngOnInit(): void {
    this.mast.list().subscribe( {
      next: hasil => {
        this.list = hasil;
      },
      error: err => {
        console.log(err)
      }
    })
  }

  deleteProduct(id: number) {
    this.mast.deleteProduct(id)
      .subscribe(
        data => {
          console.log(data);
        },
        error => console.log(error));
  }

}
