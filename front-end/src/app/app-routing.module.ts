import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router'
import { MainComponent } from './component/main/main.component';
import { FavoritesComponent } from './component/favorites/favorites.component';
import { AccountComponent } from './component/account/account.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path:"", redirectTo: 'home', pathMatch:'full' },
  { path:'home', component: MainComponent },
  { path: 'favorites', component: FavoritesComponent, canActivate:[AuthGuard] },
  { path: 'account', component: AccountComponent, canActivate:[AuthGuard] },
  { path: '**', redirectTo: 'home' },
]

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
