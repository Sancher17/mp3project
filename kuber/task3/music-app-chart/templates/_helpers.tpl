
# Set the current date
{{- define "mychart.labels.date" -}}
  {{- now }}
{{- end }}

# Set the version label
{{- define "mychart.labels.version" -}}
  1.0.0
{{- end }}
