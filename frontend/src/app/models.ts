export type TicketStatus = 'OPEN' | 'IN_PROGRESS' | 'RESOLVED';
export type TicketPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
export type TicketCategory = 'NETWORK' | 'HARDWARE' | 'SOFTWARE' | 'ACCOUNTS' | 'AUDIO_VISUAL' | 'OTHER';

export interface Ticket {
  id: number;
  title: string;
  requester: string;
  location: string;
  description: string;
  category: TicketCategory;
  priority: TicketPriority;
  status: TicketStatus;
  createdAt: string;
  updatedAt: string;
}

export interface TicketPayload {
  title: string;
  requester: string;
  location: string;
  description: string;
  category: TicketCategory;
  priority: TicketPriority;
  status?: TicketStatus;
}

export interface Dashboard {
  total: number;
  open: number;
  inProgress: number;
  resolved: number;
  critical: number;
}
