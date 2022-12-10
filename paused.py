#!/usr/bin/python

# CS 3372, fall 2021
# Audio generation example: success melody
#
# This Python file, when given to csbuild.bat, generates a Csound file,
# which is used to generate audio files.  You can also edit the Csound file
# directly and use csbuild.bat to generate audio files from it.

print('''; CS 3372, fall 2021
; Audio generation example: success melody
;
; This Csound file, when given to csbuild.bat, generates audio files.

<CsoundSynthesizer>
<CsOptions>
</CsOptions>
<CsInstruments>

sr     = 44100
ksmps  = 1
nchnls = 2
0dbfs  = 1

giSine ftgen 0, 0, 2^16, 10, 1
giPretty ftgen 0, 0, 2^16, 10, 1, 0.5, 0.75, 0.25
giUgly ftgen 0, 0, 2^16, 10, 0.1, 0.2, 0.3, 0.2, 0.5, 0.2, 0.5, 0.2, 0.1, 0.1

; plain sine instrument
;          p2        p3      p4  p5   p6        p7
; i "sine" startTime noteDur amp freq attackDur decayDur
           instr   sine
p3         =       p3 + p7
iNoteDur   =       p3
iAmp       =       p4
iFreq      =       p5
iAttackDur =       p6
iDecayDur  =       p7
iCurve     =       2
kEnv       transeg 0, iAttackDur, iCurve, iAmp, iNoteDur - iAttackDur - iDecayDur, 0, iAmp, iDecayDur, -iCurve, 0
aOut       poscil3 kEnv, iFreq, giSine
           outs    aOut, aOut
           endin

; more complex instrument
;             p2        p3      p4  p5   p6        p7
; i "complex" startTime noteDur amp freq attackDur decayDur
           instr   complex
p3         =       p3 + p7
iNoteDur   =       p3
iAmp       =       p4
iFreq      =       p5
iAttackDur =       p6
iDecayDur  =       p7
iCurve     =       3
kEnv       transeg 0, iAttackDur, iCurve, iAmp, iNoteDur - iAttackDur - iDecayDur, 0, iAmp, iDecayDur, -iCurve, 0
aOut       poscil3 kEnv, iFreq, giPretty
           outs    aOut, aOut
           endin

</CsInstruments>
<CsScore>''')

# number of beats per minute
tempo = 480.0

# note numbers and note lengths for the first melody
melody1 = [6, 10, 4, 8, 3, 0] # these are note numbers; use None to create a rest
lengths1 = [2, 1, 1, 3, 1, 1] # these are note lengths in beats


# given a note number, return the frequency for that pitch
def getPitchFromScale(whichPitch):
   # just-intonation scale from middle C
   scale = [264.0, 281.6, 297.0, 316.8, 330.0, 352.0, 371.25, 396.0, 422.4, 440.0, 475.2, 495.0]
   octave = 1.0
   try:
      # if whichPitch is an integer, return a frequency for that note number
      while whichPitch < 0:
         whichPitch += len(scale)
         octave /= 2.0
      while whichPitch >= len(scale):
         whichPitch -= len(scale)
         octave *= 2.0
      return scale[whichPitch] * octave
   except TypeError:
      # if whichPitch is something else (such as None), return 0 to create no sound
      return 0

# generate notes in Csound, one for each note in the first melody
print('; falling melody:')
startTime = 0.0 # start at the beginning for the first note
for noteNum, noteLength in zip(melody1, lengths1):
   # use the note number and length of the current note to determine arguments to the instrument
   noteDuration = 60.0 * noteLength / tempo
   attackDuration = 0.02
   decayDuration = 0.25
   amp = 0.2
   pitch = getPitchFromScale(noteNum)
   # use the sine instrument with arguments to sound one note
   print('i "sine" ' + str(startTime) + ' ' + str(noteDuration) + ' ' + str(amp) + ' ' + str(pitch) + ' ' + str(attackDuration) + ' ' + str(decayDuration))
   startTime += noteDuration # advance to the time for the next note


print('''e

</CsScore>
</CsoundSynthesizer>''')
