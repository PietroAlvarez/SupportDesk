import { CommonModule } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { ApiService } from './api.service';
import { Dashboard, Ticket, TicketCategory, TicketPayload, TicketPriority, TicketStatus } from './models';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  readonly tickets = signal<Ticket[]>([]);
  readonly dashboard = signal<Dashboard>({ total: 0, open: 0, inProgress: 0, resolved: 0, critical: 0 });
  readonly loading = signal(true);
  readonly saving = signal(false);
  readonly message = signal('');

  search = '';
  statusFilter = '';
  form: TicketPayload = this.emptyForm();

  readonly categories: TicketCategory[] = ['NETWORK', 'HARDWARE', 'SOFTWARE', 'ACCOUNTS', 'AUDIO_VISUAL', 'OTHER'];
  readonly priorities: TicketPriority[] = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];

  constructor(private readonly api: ApiService) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.loading.set(true);
    forkJoin({
      tickets: this.api.getTickets(this.search, this.statusFilter),
      dashboard: this.api.getDashboard()
    }).subscribe({
      next: ({ tickets, dashboard }) => {
        this.tickets.set(tickets);
        this.dashboard.set(dashboard);
        this.loading.set(false);
      },
      error: () => {
        this.message.set('No se pudo conectar con la API. Comprueba que Spring Boot esté iniciado.');
        this.loading.set(false);
      }
    });
  }

  createTicket(): void {
    this.saving.set(true);
    this.api.createTicket(this.form).subscribe({
      next: () => {
        this.form = this.emptyForm();
        this.message.set('Ticket creado correctamente.');
        this.saving.set(false);
        this.refresh();
      },
      error: () => {
        this.message.set('Revisa los datos del ticket.');
        this.saving.set(false);
      }
    });
  }

  advance(ticket: Ticket): void {
    this.api.advanceTicket(ticket.id).subscribe(() => this.refresh());
  }

  remove(ticket: Ticket): void {
    if (!confirm(`¿Eliminar el ticket “${ticket.title}”?`)) return;
    this.api.deleteTicket(ticket.id).subscribe(() => this.refresh());
  }

  statusLabel(status: TicketStatus): string {
    return { OPEN: 'Abierto', IN_PROGRESS: 'En curso', RESOLVED: 'Resuelto' }[status];
  }

  priorityLabel(priority: TicketPriority): string {
    return { LOW: 'Baja', MEDIUM: 'Media', HIGH: 'Alta', CRITICAL: 'Crítica' }[priority];
  }

  categoryLabel(category: TicketCategory): string {
    return {
      NETWORK: 'Redes', HARDWARE: 'Hardware', SOFTWARE: 'Software', ACCOUNTS: 'Cuentas',
      AUDIO_VISUAL: 'Audio y video', OTHER: 'Otro'
    }[category];
  }

  nextLabel(status: TicketStatus): string {
    return status === 'OPEN' ? 'Tomar ticket' : 'Resolver';
  }

  private emptyForm(): TicketPayload {
    return { title: '', requester: '', location: '', description: '', category: 'NETWORK', priority: 'MEDIUM' };
  }
}
