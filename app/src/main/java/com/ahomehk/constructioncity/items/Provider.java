package com.ahomehk.constructioncity.items;

/**
 * Created by rainvic on 29/4/15.
 */
public class Provider {

    private String _provider_id;
    private String provider_title;
    private String provider_contact;
    private String provider_email;
    private String provider_extra_info;
    private String provider_created_at;
    private String provider_link;

    public Provider() {
    }

    public Provider(int _provider_id, String provider_title,
                    String provider_contact, String provider_email,
                    String provider_extra_info, String provider_created_at,
                    String provider_link) {
        this._provider_id = Integer.toString(_provider_id);
        this.provider_title = provider_title;
        this.provider_contact = provider_contact;
        this.provider_email = provider_email;
        this.provider_extra_info = provider_extra_info;
        this.provider_created_at = provider_created_at;
        this.provider_link = provider_link;
    }

    public Provider(Provider clone){
        this._provider_id = clone.get_provider_id_str();
        this.provider_title = clone.getProvider_title();
        this.provider_contact = clone.getProvider_contact();
        this.provider_email = clone.getProvider_email();
        this.provider_extra_info = clone.getProvider_extra_info();
        this.provider_created_at = clone.getProvider_created_at();
        this.provider_link = clone.getProvider_link();
    }

    public int get_provider_id() {
        return Integer.parseInt(_provider_id);
    }
    public String get_provider_id_str() {
        return _provider_id;
    }
    public void set_provider_id(int _provider_id) {
        this._provider_id = Integer.toString(_provider_id);
    }
    public void set_provider_id(String _provider_id) {
        this._provider_id = _provider_id;
    }

    public String getProvider_title() {
        return provider_title;
    }

    public void setProvider_title(String provider_title) {
        this.provider_title = provider_title;
    }

    public String getProvider_contact() {
        return provider_contact;
    }

    public void setProvider_contact(String provider_contact) {
        this.provider_contact = provider_contact;
    }

    public String getProvider_email() {
        return provider_email;
    }

    public void setProvider_email(String provider_email) {
        this.provider_email = provider_email;
    }

    public String getProvider_extra_info() {
        return provider_extra_info;
    }

    public void setProvider_extra_info(String provider_extra_info) {
        this.provider_extra_info = provider_extra_info;
    }

    public String getProvider_created_at() {
        return provider_created_at;
    }

    public void setProvider_created_at(String provider_created_at) {
        this.provider_created_at = provider_created_at;
    }

    public String getProvider_link() {
        return provider_link;
    }

    public void setProvider_link(String provider_link) {
        this.provider_link = provider_link;
    }
}
