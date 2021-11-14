package com.chevrolet.MusicSyncPlaylist.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.playlist",
        joinColumns = @JoinColumn(name = "con_pla_id",nullable = false)),
    @AssociationOverride(name = "primaryKey.track",
        joinColumns = @JoinColumn(name = "con_tra_id",nullable = false)) })
public class Content {

    @EmbeddedId
	private ContentId primaryKey;
    
	public Content() {
    	
    }
    public Content(ContentId pk)
    {
    	this.primaryKey = pk;
    }
    
    public ContentId getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(ContentId primaryKey) {
		this.primaryKey = primaryKey;
	}
}
