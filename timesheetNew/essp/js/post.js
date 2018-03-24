'POST代替FORM 
'//readyState是xmlhttp返回数据的进度，0=载入中,1=未初始化,2=已载入,3=运行中,4=完成

Function URLEncoding(vstrIn) 
    strReturn = "" 
    For i = 1 To Len(vstrIn) 
        ThisChr = Mid(vStrIn,i,1) 
        If Abs(Asc(ThisChr)) < &HFF Then 
            strReturn = strReturn & ThisChr 
        Else 
            innerCode = Asc(ThisChr) 
            If innerCode < 0 Then 
                innerCode = innerCode + &H10000 
            End If 
            Hight8 = (innerCode  And &HFF00)\ &HFF 
            Low8 = innerCode And &HFF 
            strReturn = strReturn & "%" & Hex(Hight8) &  "%" & Hex(Low8) 
        End If 
    Next 
    URLEncoding = strReturn 
End Function 
Function bytes2BSTR(vIn) 
    strReturn = "" 
    For i = 1 To LenB(vIn) 
        ThisCharCode = AscB(MidB(vIn,i,1)) 
        If ThisCharCode < &H80 Then 
            strReturn = strReturn & Chr(ThisCharCode) 
        Else 
            NextCharCode = AscB(MidB(vIn,i+1,1)) 
            strReturn = strReturn & Chr(CLng(ThisCharCode) * &H100 + CInt(NextCharCode)) 
            i = i + 1 
        End If 
    Next 
    bytes2BSTR = strReturn 
End Function 

sub test() 
dim strA,oReq 
strA = URLEncoding("submit1=Submit&text1=中文") 
set oReq = CreateObject("MSXML2.XMLHTTP") 
oReq.open "POST","http://ServerName/VDir/TstResult.asp",false 
oReq.setRequestHeader "Content-Length",Len(strA) 
oReq.setRequestHeader "CONTENT-TYPE","application/x-www-form-urlencoded" 
oReq.send strA 
msgbox bytes2BSTR(oReq.responseBody) 
end sub



