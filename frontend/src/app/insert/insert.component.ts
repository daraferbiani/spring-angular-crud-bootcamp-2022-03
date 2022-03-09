import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MasterService} from "../services/master.service";
import {Category} from "../model/category.model";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-insert',
  templateUrl: './insert.component.html',
  styleUrls: ['./insert.component.css']
})
export class InsertComponent implements OnInit {
  formProduct!: FormGroup;
  id!: number;

  // blankspaces(control: FormControl):{[s:string]: boolean} | null{
  //   if (control.value != null && ty control.value.trim().length === 0 ){
  //     return {'blankspaces': true};
  //   }
  //   return null;
  // }

  constructor(private toastr: ToastrService,
              private formBuild: FormBuilder,
              private mast: MasterService,
              private router: Router,
              private route: ActivatedRoute) {
    this.formCategory = this.formBuild.group({
      'id': new FormControl(null, [Validators.required, Validators.minLength(1)]),
      'name': new FormControl(null, [Validators.required, Validators.minLength(1)]),
      'category': new FormControl(null, [Validators.required, Validators.minLength(10)]),
      'price': new FormControl(null, [Validators.required, Validators.minLength(10)])
    })
  }

  ngOnInit(): void {
    this.route.params.subscribe(rute => {
      this.id = parseInt(rute['id']);
      if (this.id) {
        console.log(this.id)
        this.mast.getCategoryId(this.id).subscribe({
          next: value => {
            this.formProduct.controls['id'].setValue(vaid)
            this.formProduct.controls['name'].setValue(value.name)
            this.formProduct.controls['description'].setValue(value.description)
          }
        })
      }
    })

  }

  simpan(): void {
    console.log(this.formCategory.controls)
    console.log(this.formCategory.valid)
    if (this.formCategory.valid) {
      let category = <Category>{};
      category.category_id = this.formCategory.controls['category_id'].value
      category.department_id = this.formCategory.controls['department_id'].value
      category.name = this.formCategory.controls['name'].value
      category.description = this.formCategory.controls['description'].value
      if (this.id) {
        category.category_id = this.id;
      }
      this.mast.saveCategory(category).subscribe({
        next: hasil => {
          // alert('simpan berhasil')
          this.toastr.success(hasil.status, 'simpan berhasil')
          this.router.navigateByUrl('/update/' + category.category_id)
        },
        error: err => {
          const pesan: any[] = err.error.status;
          let msg = '';
          for (let i = 0; i < pesan.length; i++) {
            msg += pesan[i].field+ " : " + pesan[i].defaultMessage +"\n";
          }
          console.log(msg)
          this.toastr.error(msg, 'Error!', {
            positionClass:'toast-top-center'
          })
        }
      });
    } else {
      // alert("Form harus terisi semua")
      this.toastr.error("Form harus terisi semua", "GAGAL!!", {
        positionClass: 'toast-top-center'
      })

    }
  }

  update(): void {
    if (this.formCategory.valid) {
      let category = <Category>{};
      category.category_id = this.formCategory.controls['category_id'].value
      category.department_id = this.formCategory.controls['department_id'].value
      category.name = this.formCategory.controls['name'].value
      category.description = this.formCategory.controls['description'].value
      if (this.id) {
        category.category_id = this.id;
      }
      this.mast.updateProduct(product).subscribe({
        next: hasil => {
          alert('update berhasil')
          this.router.navigateByUrl('/home')
        },
        error: err => {
          console.log(err)
        }
      });
    } else {
      alert("Form harus terisi semua")
    }
  }
}
