import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Dashboard, Ticket, TicketPayload, TicketStatus } from './models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly baseUrl = this.resolveBaseUrl();

  constructor(private readonly http: HttpClient) {}

  private resolveBaseUrl(): string {
    return window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1'
      ? 'http://localhost:8081/api'
      : '/api';
  }

  getDashboard() {
    return this.http.get<Dashboard>(`${this.baseUrl}/dashboard`);
  }

  getTickets(search = '', status = '') {
    let params = new HttpParams();
    if (search.trim()) params = params.set('search', search.trim());
    if (status) params = params.set('status', status);
    return this.http.get<Ticket[]>(`${this.baseUrl}/tickets`, { params });
  }

  createTicket(payload: TicketPayload) {
    return this.http.post<Ticket>(`${this.baseUrl}/tickets`, payload);
  }

  advanceTicket(id: number) {
    return this.http.patch<Ticket>(`${this.baseUrl}/tickets/${id}/advance`, {});
  }

  deleteTicket(id: number) {
    return this.http.delete<void>(`${this.baseUrl}/tickets/${id}`);
  }
}
