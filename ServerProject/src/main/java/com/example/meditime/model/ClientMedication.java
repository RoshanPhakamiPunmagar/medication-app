    // Amy Wickham 12178502
    package com.example.meditime.model;

    import jakarta.persistence.*;
    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.List;
    /**
     * Entity representing the medication assigned to a specific client.
     *
     * Includes details such as the medication itself, dosage, treatment start and end dates,
     * the current pause status, and scheduled times for medication intake.
     * Links to both the Client and Medication entities.
     */
    @Entity
    public class ClientMedication {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long clientMedicationId;

        @ManyToOne
        private Client client;

        @ManyToOne
        private Medication medication;

        private String dosage;
        private LocalDate startDate;
        private LocalDate endDate;

        private boolean isPaused;

        @ElementCollection
        private List<LocalTime> scheduledTimes;

        // Getters and Setters
        public Long getClientMedicationId() {
            return clientMedicationId;
        }

        public void setClientMedicationId(Long clientMedicationId) {
            this.clientMedicationId = clientMedicationId;
        }

        public Client getClient() {
            return client;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public Medication getMedication() {
            return medication;
        }

        public void setMedication(Medication medication) {
            this.medication = medication;
        }

        public String getDosage() {
            return dosage;
        }

        public void setDosage(String dosage) {
            this.dosage = dosage;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public boolean isPaused() {
            return isPaused;
        }

        public void setPaused(boolean paused) {
            isPaused = paused;
        }

        public List<LocalTime> getScheduledTimes() {
            return scheduledTimes;
        }

        public void setScheduledTimes(List<LocalTime> scheduledTimes) {
            this.scheduledTimes = scheduledTimes;
        }
    }
