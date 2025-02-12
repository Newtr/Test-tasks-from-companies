package com.example.MyApp.PropertyView.Application.DTO;

import lombok.Data;
import com.example.MyApp.PropertyView.Domain.Model.Contacts;

@Data
public class ContactsDTO {
    private String phone;
    private String email;
    
    public static ContactsDTO fromContacts(Contacts contacts) {
        if (contacts == null) {
            return null;
        }
        ContactsDTO dto = new ContactsDTO();
        dto.setPhone(contacts.getPhone());
        dto.setEmail(contacts.getEmail());
        return dto;
    }
}
