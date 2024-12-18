package models.bean;

public class File {
	public int ID;
	public String UUID;
	public String FileName;
	public String Status;
	public String Path;
	public File(int ID,String FileName,String UUID,String Status,String Path)
	{
		this.ID=ID;
		this.UUID=UUID;
		this.Path=Path;
		this.FileName=FileName;
		this.Status=Status;
	}
    public String getUUID() {
        return UUID;
    }
    public String getPath() {
        return Path;
    }
}
