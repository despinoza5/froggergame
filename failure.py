#!/usr/bin/python

# CS 3372, fall 2021
# Audio generation example: failure melody
#
# This Python file, when given to csbuild.bat, generates a Csound file,
# which is used to generate audio files.  You can also edit the Csound file
# directly and use csbuild.bat to generate audio files from it.

from math import sqrt
from random import uniform

print('''; CS 3372, fall 2021
; Audio generation example: failure melody
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

; varying-power-of-sine instrument
;               p2        p3      p4  p5   p6       p7        p8
; i "sinepower" startTime noteDur amp freq maxPower attackDur decayDur
           instr      sinepower
p3         =          p3 + p8
iNoteDur   =          p3
iAmp       =          p4
iFreq      =          p5
iMaxPower  =          p6
iAttackDur =          p7
iDecayDur  =          p8
iCurve     =          2
kEnv       transeg    0, iAttackDur, -iCurve, iAmp, iNoteDur - iAttackDur - iDecayDur, -iCurve, iAmp / 2, iDecayDur, -iCurve, 0
kPower     transeg    1, iAttackDur, -iCurve, iMaxPower, iNoteDur - iAttackDur - iDecayDur, -iCurve, sqrt(iMaxPower), iDecayDur, -iCurve, 1
aSine      poscil3    1, iFreq, giSine
aPowered   powershape aSine, kPower
aOut       =          kEnv * aPowered
           outs       aOut, aOut
           endin

; FM synthesis instrument
;        p2        p3      p4  p5   p6        p7       p8        p9       p10
; i "fm" startTime noteDur amp freq attackDur decayDur carrRatio modRatio fmIndex
           instr   fm
p3         =       p3 + p7
iNoteDur   =       p3
iAmp       =       p4
iFreq      =       p5
iAttackDur =       p6
iDecayDur  =       p7
iCurve     =       2
kEnv       transeg 0, iAttackDur, iCurve, iAmp, iNoteDur - iAttackDur - iDecayDur, 0, iAmp, iDecayDur, -iCurve, 0
aOut       foscili kEnv, iFreq, p8, p9, p10, giSine
           outs    aOut, aOut
           endin

</CsInstruments>
<CsScore>''')

# number of beats per minute
tempo = 480.0

# note numbers and note lengths for the first melody
melody1 = [6, 0, 6, 0, 6, 0] # these are note numbers; use None to create a rest
lengths1 = [2, 1, 2, 1, 2, 1] # these are note lengths in beats

# note numbers and note lengths for the second melody
melody2 = [11, 10, 2, 1] # these are note numbers; use None to create a rest
lengths2 = [1, 3, 1, 4] # these are note lengths in beats

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
print('; background melody:')
startTime = 0.0 # start at the beginning for the first note
for noteNum, noteLength in zip(melody1, lengths1):
   # use the note number and length of the current note to determine arguments to the instrument
   noteDuration = 60.0 * noteLength / tempo
   attackDuration = 0.02
   decayDuration = 0.25
   amp = 0.2
   pitch = getPitchFromScale(noteNum)
   maxPower = 2.0
   # use the sinepower instrument with arguments to sound one note
   print('i "sinepower" ' + str(startTime) + ' ' + str(noteDuration) + ' ' + str(amp) + ' ' + str(pitch) + ' ' + str(maxPower) + ' ' + str(attackDuration) + ' ' + str(decayDuration))
   startTime += noteDuration # advance to the time for the next note

# generate notes in Csound, one for each note in the second melody
print('; sad melody:')
startTime = 0.0 # start at the beginning for the first note
for noteNum, noteLength in zip(melody2, lengths2):
    # use the note number and length of the current note to determine arguments to the instrument
   noteDuration = 60.0 * noteLength / tempo
   attackDuration = 0.02
   decayDuration = 0.25
   amp = 0.2
   pitch = getPitchFromScale(noteNum)
   carrRatio = 1.0
   modRatio = sqrt(5.0)
   fmIndex = 1.0
   # use the FM instrument with arguments to sound one note
   print('i "fm" ' + str(startTime) + ' ' + str(noteDuration) + ' ' + str(amp) + ' ' + str(pitch) + ' ' + str(attackDuration) + ' ' + str(decayDuration) + ' ' + str(carrRatio) + ' ' + str(modRatio) + ' ' + str(fmIndex))
   startTime += noteDuration # advance to the time for the next note

print('''e

</CsScore>
</CsoundSynthesizer>''')
