package dev.pietro.supportdesk.api;

public record DashboardResponse(long total, long open, long inProgress, long resolved, long critical) {
}
