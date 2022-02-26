package com.example.gui.service;

import com.example.gui.domain.Message;
import com.example.gui.domain.validators.ValidationException;
import com.example.gui.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MessageService {
    private final Repository<Long, Message> messageRepository;

    public MessageService(Repository<Long, Message> messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(Long from, Long to, String text) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Id's must be not null");
        }
        List<Message> conversation = showConversation(from, to);
        Message newMessage = new Message(from, to, text, LocalDateTime.now(), 0L);
        if (conversation.size() == 0 || conversation.get(conversation.size() - 1).getFrom().equals(from)) {
            newMessage.setReply(0L);
        } else {
            newMessage.setReply(from);
        }
        try {
            this.messageRepository.save(newMessage);
        } catch (ValidationException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public List<Message> showConversation(Long id1, Long id2) {
        List<Message> conversation = new ArrayList<>();
        for (Message message : findAll()) {
            if ((message.getFrom().equals(id1) && message.getTo().equals(id2)) || (message.getFrom().equals(id2) && message.getTo().equals(id1))) {
                conversation.add(message);
            }
        }
        return conversation.stream()
                .sorted(Comparator.comparing(Message::getDate))
                .collect(Collectors.toList());
    }

    public void removeMessage(Long id) {
        try {
            messageRepository.delete(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Message findOne(Long x) {
        try {
            return this.messageRepository.findOne(x);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterable<Message> findAll() {
        return this.messageRepository.findAll();
    }
}
