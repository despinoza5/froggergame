:: CS 3372, fall 2021
:: Audio creation
:: Drag and drop a .py or .csd file onto this batch file to create a .wav file.

setlocal
set python="C:\Program Files\Python39\python.exe"
set csound="C:\Program Files\Csound6_x64\bin\csound.exe"

set pyfile="%~1"
set csdfile=%pyfile:.py=.csd%
:: use Python file to generate .csd file:
if "%~x1"==".py" %python% < %pyfile% > %csdfile%

set wavfile=%csdfile:.csd=.wav%
:: use .csd file to generate .wav file:
%csound% -R -format=wav:long -o %wavfile% %csdfile%

endlocal
pause
