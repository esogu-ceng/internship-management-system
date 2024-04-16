// App.tsx
import React, { useState } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import Modal from 'react-modal';

const localizer = momentLocalizer(moment);

interface Event {
  title: string;
  start: Date;
  end: Date;
  description?: string;
  approved: boolean;
}

const App: React.FC = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [selectedEvent, setSelectedEvent] = useState<Event | null>(null);
  const [modalIsOpen, setModalIsOpen] = useState<boolean>(false);
  const [dailyLog, setDailyLog] = useState<string>('');

  const handleSelectEvent = (event: Event) => {
    setSelectedEvent(event);
    setModalIsOpen(true);
  };

  const handleModalClose = () => {
    setSelectedEvent(null);
    setModalIsOpen(false);
  };

  const handleLogSubmit = () => {
    if (selectedEvent) {
      const updatedEvents = events.map((event) =>
        event === selectedEvent ? { ...event, description: dailyLog } : event
      );
      setEvents(updatedEvents);
      setDailyLog('');
      handleModalClose();
    }
  };

  return (
    <div className="App">
      <h1>Öğrenci Staj Takvimi</h1>
      <Calendar
        localizer={localizer}
        events={events}
        startAccessor="start"
        endAccessor="end"
        style={{ height: 500 }}
        onSelectEvent={handleSelectEvent}
      />
      <Modal isOpen={modalIsOpen} onRequestClose={handleModalClose}>
        <h2>Günlük</h2>
        <textarea value={dailyLog} onChange={(e) => setDailyLog(e.target.value)} />
        <button onClick={handleLogSubmit}>Kaydet</button>
        <button onClick={handleModalClose}>Kapat</button>
      </Modal>
    </div>
  );
};

export default App;
