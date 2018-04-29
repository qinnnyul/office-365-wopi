package com.example.office365wopi.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CheckFileInfoResponse implements Serializable {
    /**
     * The string name of the file, including extension, without a path. Used for display in user interface (UI), and determining the extension of the file.
     */
    @JsonProperty("BaseFileName")
    public String baseFileName;

    /**
     * A string that uniquely identifies the owner of the file. In most cases, the user who uploaded or created the file should be considered the owner.
     */
    @JsonProperty("OwnerId")
    public String ownerId;

    /**
     * The size of the file in bytes, expressed as a long, a 64-bit signed integer.
     */
    @JsonProperty("Size")
    public long size;

    /**
     * A 256 bit SHA-2-encoded [FIPS 180-2] hash of the file contents, as a Base64-encoded string. Used for caching purposes in WOPI clients.
     */
    @JsonProperty("SHA256")
    public String sha256;

    /**
     * The current version of the file based on the server’s file version schema, as a string. This value must change when the file changes, and version values must never repeat for a given file.
     */
    @JsonProperty("Version")
    public long version;

    /**
     * A Boolean value that indicates a WOPI client may allow connections to external services referenced in the file (for example, a marketplace of embeddable JavaScript apps).
     */
    @JsonProperty("AllowExternalMarketplace")
    public boolean allowExternalMarketplace;

    /**
     * A Boolean value that indicates that the user has permission to alter the file. Setting this to true tells the WOPI client that it can call PutFile on behalf of the user.
     */
    @JsonProperty("UserCanWrite")
    public boolean userCanWrite;

    /**
     * A Boolean value that indicates that the host supports the following WOPI operations:
     */
    @JsonProperty("SupportsUpdate")
    public boolean supportsUpdate;

    /**
     * A Boolean value that indicates that the host supports the following WOPI operations:
     */
    @JsonProperty("SupportsLocks")
    public boolean supportsLocks;

    @JsonIgnore
    public String getBaseFileName() {
        return baseFileName;
    }

    public void setBaseFileName(String baseFileName) {
        this.baseFileName = baseFileName;
    }

    @JsonIgnore
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @JsonIgnore
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @JsonIgnore
    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    @JsonIgnore
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @JsonIgnore
    public boolean isAllowExternalMarketplace() {
        return allowExternalMarketplace;
    }

    public void setAllowExternalMarketplace(boolean allowExternalMarketplace) {
        this.allowExternalMarketplace = allowExternalMarketplace;
    }

    @JsonIgnore
    public boolean isUserCanWrite() {
        return userCanWrite;
    }

    public void setUserCanWrite(boolean userCanWrite) {
        this.userCanWrite = userCanWrite;
    }

    @JsonIgnore
    public boolean isSupportsUpdate() {
        return supportsUpdate;
    }

    public void setSupportsUpdate(boolean supportsUpdate) {
        this.supportsUpdate = supportsUpdate;
    }

    @JsonIgnore
    public boolean isSupportsLocks() {
        return supportsLocks;
    }

    public void setSupportsLocks(boolean supportsLocks) {
        this.supportsLocks = supportsLocks;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "baseFileName='" + baseFileName + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", size=" + size +
                ", sha256='" + sha256 + '\'' +
                ", version=" + version +
                ", allowExternalMarketplace=" + allowExternalMarketplace +
                ", userCanWrite=" + userCanWrite +
                ", supportsUpdate=" + supportsUpdate +
                ", supportsLocks=" + supportsLocks +
                '}';
    }
}
