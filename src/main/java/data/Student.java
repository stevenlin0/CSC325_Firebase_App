package data;

public class Student {
    private Integer id; private String first,last,dept,major,email,imageUrl;
    public Student() {}  // needed by Firestore
    public Student(Integer id,String f,String l,String d,String m,String e,String url){
        this.id=id;first=f;last=l;dept=d;major=m;email=e;imageUrl=url;}
    public Integer getId(){return id;} public String getFirst(){return first;}
    public String getLast(){return last;} public String getDept(){return dept;}
    public String getMajor(){return major;} public String getEmail(){return email;}
    public String getImageUrl(){return imageUrl;}
}
